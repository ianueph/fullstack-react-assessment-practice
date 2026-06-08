{
  description = "A Nix-flake based fullstack project involving Spring Boot, Vite, and PostgreSQL";

  inputs.nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable"; # unstable Nixpkgs

  outputs =
    { self, ... }@inputs:

    let
      javaVersion = 21; # Change this value to update the whole stack
      postgreSqlVersion = 17;

      supportedSystems = [
        "x86_64-linux"
        "aarch64-linux"
        "aarch64-darwin"
      ];
      forEachSupportedSystem =
        f:
        inputs.nixpkgs.lib.genAttrs supportedSystems (
          system:
          f {
            inherit system;
            pkgs = import inputs.nixpkgs {
              inherit system;
              overlays = [ inputs.self.overlays.default ];
            };
          }
        );
    in
    {
      overlays.default =
        final: prev:
        let
          jdk = prev."jdk${toString javaVersion}";
          postgresql = prev."postgresql_${toString postgreSqlVersion}";
        in
        rec {
          inherit jdk;
          inherit postgresql;
          maven = prev.maven.override { jdk_headless = jdk; };
          gradle = prev.gradle.override { java = jdk; };
          lombok = prev.lombok.override { inherit jdk; };
          nodejs = prev.nodejs;
          yarn = (prev.yarn.override { inherit nodejs; });
        };

      devShells = forEachSupportedSystem (
        { pkgs, system }:
        {
          default = pkgs.mkShellNoCC {
            packages = with pkgs; [
              jdk
              maven
              gradle
              nodejs
              postgresql
            ];
          };

          backend = pkgs.mkShellNoCC {
            packages = with pkgs; [
              gcc
              gradle
              jdk
              maven
              ncurses
              patchelf
              zlib
              self.formatter.${system}
            ];

            shellHook =
              let
                loadLombok = "-javaagent:${pkgs.lombok}/share/java/lombok.jar";
                prev = "\${JAVA_TOOL_OPTIONS:+ $JAVA_TOOL_OPTIONS}";
              in
              ''
                export JAVA_TOOL_OPTIONS="${loadLombok}${prev}"
              '';
          };
          
          frontend = pkgs.mkShellNoCC {
            packages = with pkgs; [
              nodejs
              pnpm
              yarn
              self.formatter.${system}
            ];
          };

          database = pkgs.mkShell {
            packages = with pkgs; [
              postgresql
              self.formatter.${system}
            ];
          };

          shellHook = ''
            echo "PostgreSQL database from config $PWD started..."
          '';

          # postgres server start command
          # pg_ctl -D database/mydb -l logfile -o "--unix_socket_directories='$PWD'" start

          # postgres stop command
          # pg_ctl -D database/mydb stop
        }
      );

      packages = forEachSupportedSystem (
        { pkgs, system }:
        with pkgs; {
          backend = maven.buildMavenPackage (finalAttrs: {
            pname = "practice-backend";
            version = "0.0.1";

            src = ./practice-backend;

            mvnHash = "sha256-1Yk1PoFEfUfPzAvaivBEXB2NQbGKyccLoi9CPKsPv1g=";

            nativeBuildInputs = [ makeWrapper ];

            doCheck = false;

            installPhase = ''
              runHook preInstall

              mkdir -p $out/bin $out/share/practice-backend
              install -Dm644 target/practice-backend-*.jar $out/share/java/practice-backend.jar

              makeWrapper ${jre}/bin/java $out/bin/practice-backend \
                --add-flags "-jar $out/share/java/practice-backend.jar"

              runHook postInstall
            '';

            meta = {
              description = "This is a copy of an old college project, of which it's architecture is used here for practice purposes.";
            };
          });

          frontend = buildNpmPackage {
            pname = "practice-frontend";
            version = "0.1";

            src = ./practice-frontend/.;

            installPhase = ''
              mkdir -p $out/share/www $out/bin

              cp -r dist/* $out/share/www

              cat <<EOF > $out/bin/practice-frontend
              #!/bin/sh
              echo "Starting Busybox HTTP server at http://localhost:8081 ... "
              exec ${busybox}/bin/httpd -f -p 8081 -h $out/share/www
              EOF
              
              chmod +x $out/bin/practice-frontend
            '';

            npmDepsHash = "sha256-wungYJB8jpozzHakyc7zKN7t+WxelK72WQjfcC0Im1M=";
          };
        }
      );

      formatter = forEachSupportedSystem ({ pkgs, ... }: pkgs.nixfmt);
    };
}
