import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { HomePage } from './pages/Home.page';
import { Events } from './pages/Events.page';

const router = createBrowserRouter([
  {
    path: '/',
    element: <HomePage />,
  },
  {
    path: "events",
    element: <Events />
  }
]);

export function Router() {
  return <RouterProvider router={router} />;
}
