import { FormProps } from "@/types/FormProps";
import { Button, Group, Stack, Textarea, TextInput, useProps } from "@mantine/core";
import axios from "axios";
import { useState } from "react";

export function EventForm(
    { onCreated } : 
    FormProps
) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");

    const [error, setError] = useState("");

    const handleSubmit = async (e: React.SubmitEvent) => {
        e.preventDefault();

        try {
            const response = await axios.post("http://localhost:8080/events",
                {
                    name: name,
                    description: description
                }
            );

            if (response.status >= 200 && response.status < 300) {
                alert("Successfully sent to database.");
            }

            await onCreated();
        } catch (err) {
            const errorMessage = "Failed to send data."
            setError(errorMessage);

            alert(error);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <Stack>
                <TextInput 
                    label = "Event Name"
                    value = {name}
                    onChange={(e) => setName(e.currentTarget.value)}
                    required
                />

                <Textarea 
                    label = "Event Description"
                    value = {description}
                    onChange={(e) => setDescription(e.currentTarget.value)}
                    required
                />  

                <Group justify="flex-start">
                    <Button type="submit"> Create Event </Button>
                </Group>
            </Stack>
        </form>
    )
}