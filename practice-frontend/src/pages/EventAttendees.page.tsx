import { EventInfo } from "@/components/Event/EventInfo";
import { StudentTable } from "@/components/Student/StudentTable";
import { Student } from "@/types/Student";
import axios from "axios";
import { useEffect, useState } from "react"
import { useParams } from "react-router-dom";

export function EventAttendees() {
    const { eventId } = useParams();

    const [students, setStudents] = useState<Student[]>([]);
    const [eventName, setEventName] = useState("");
    const [eventDescription, setEventDescription] = useState("");

    const loadEvent = async () => {
        const response = await axios.get(`http://localhost:8080/events/${eventId}`);
        
        setEventName(response.data.name);
        setEventDescription(response.data.description);
    }

    const loadStudents = async () => {
        const response = await axios.get(`http://localhost:8080/events/${eventId}/students`);
        setStudents(response.data);
    }

    useEffect(() => {
        loadEvent();
        loadStudents();
    }, []);

    return <>
        <EventInfo 
            name={eventName}
            description={eventDescription}
        />
        <StudentTable students={students}/>
    </>
}