import { FormProps } from "@/types/FormProps";
import { Button, Group, Stack, Textarea, TextInput, useProps } from "@mantine/core";
import axios from "axios";
import { useState } from "react";

export function StudentForm(
    { onCreated } : 
    FormProps
) {
    const [name, setName] = useState("");
    const [studentNumber, setStudentNumber] = useState("");

    const [error, setError] = useState("");

    const handleSubmit = async (e: React.SubmitEvent) => {
        e.preventDefault();

        try {
            const response = await axios.post("http://localhost:8080/students",
                {
                    name: name,
                    studentNumber: studentNumber
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
                    label = "Student Name"
                    value = {name}
                    onChange={(e) => setName(e.currentTarget.value)}
                    required
                />

                <Textarea 
                    label = "Student Number"
                    value = {studentNumber}
                    onChange={(e) => setStudentNumber(e.currentTarget.value)}
                    required
                />  

                <Group justify="flex-start">
                    <Button type="submit"> Create Student </Button>
                </Group>
            </Stack>
        </form>
    )
}