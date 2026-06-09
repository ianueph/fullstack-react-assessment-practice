import { StudentForm } from "@/components/Student/StudentForm";
import { StudentTable } from "@/components/Student/StudentTable";
import { Student } from "@/types/Student";
import axios from "axios";
import { useEffect, useState } from "react";

export function Students() {
    const [students, setStudents] = useState<Student[]>([]);

    const loadStudents = async () => {
        const response = await axios.get("http://localhost:8080/students");
        setStudents(response.data);
    }

    useEffect(() => {
        loadStudents();
    }, []); 

    return <>
        <StudentForm onCreated={loadStudents} />
        <StudentTable students={students}/>
    </>
}