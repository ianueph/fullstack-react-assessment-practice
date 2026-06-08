import { EventForm } from "@/components/Event/EventForm";
import { EventTable } from "@/components/Event/EventTable";
import { Eventt } from "@/types/Eventt";
import axios from "axios";
import { useEffect, useState } from "react";

export function Events() {
    const [events, setEvents] = useState<Eventt[]>([]);

    const loadEvents = async () => {
        const response = await axios.get("http://localhost:8080/events");
        setEvents(response.data);
    }

    useEffect(() => {
        loadEvents();
    }, []); 

    return <>
        <EventForm onCreated={loadEvents} />
        <EventTable events={events}/>
    </>
}