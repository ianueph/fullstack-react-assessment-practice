import { Student } from "@/types/Student";
import { Table } from "@mantine/core";

export function StudentTable({ students }: { students: Student[]}) {

    return (
        <Table striped highlightOnHover>
            <Table.Thead>
                <Table.Tr>
                    <Table.Th>ID</Table.Th>
                    <Table.Th>Name</Table.Th>
                    <Table.Th>Student Number</Table.Th>
                </Table.Tr>
            </Table.Thead>

            <Table.Tbody>
                {students.map(student => (
                    <Table.Tr key={student.id}>
                        <Table.Td>{student.id}</Table.Td>
                        <Table.Td>{student.name}</Table.Td>
                        <Table.Td>{student.studentNumber}</Table.Td>
                    </Table.Tr>
                ))}
            </Table.Tbody>
        </Table>
    )
}