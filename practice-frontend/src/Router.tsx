import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { HomePage } from './pages/Home.page';
import { Events } from './pages/Events.page';
import { Students } from './pages/Students.page';

const router = createBrowserRouter([
  {
    path: '/',
    element: <HomePage />,
  },
  {
    path: "events",
    element: <Events />,
    //children: [
    //  {
    //    path: "attendees",
    //    element: <EventAttendees />
    //  }
    //]
  },
  {
    path: "students",
    element: <Students />,
    //children: [
    //  {
    //    path: "attended",
    //    element: <EventsAttended />
    //  }
    //]
  },
  {
    path: "registration/:eventId",
    //element: <EventRegistration />,
  }
]);

export function Router() {
  return <RouterProvider router={router} />;
}
