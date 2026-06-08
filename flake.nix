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

      formatter = forEachSupportedSystem ({ pkgs, ... }: pkgs.nixfmt);
    };
}
