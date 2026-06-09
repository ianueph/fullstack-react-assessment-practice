import { Eventt } from "@/types/Eventt";
import { Table } from "@mantine/core";
import { useNavigate } from "react-router-dom";

export function EventTable({ events }: { events: Eventt[]}) {

    const navigate = useNavigate();

    const handleRowClick = (id: number) => {
        navigate(`/events/${id}`)
    }

    return (
        <Table striped highlightOnHover>
            <Table.Thead>
                <Table.Tr>
                    <Table.Th>ID</Table.Th>
                    <Table.Th>Name</Table.Th>
                    <Table.Th>Description</Table.Th>
                </Table.Tr>
            </Table.Thead>

            <Table.Tbody>
                {events.map(eventt => (
                    <Table.Tr 
                        key={eventt.id} 
                        onClick={() => handleRowClick(eventt.id)}
                        style={{
                            cursor: "pointer",
                            userSelect: "none"
                        }}
                    >
                        <Table.Td>{eventt.id}</Table.Td>
                        <Table.Td>{eventt.name}</Table.Td>
                        <Table.Td>{eventt.description}</Table.Td>
                    </Table.Tr>
                ))}
            </Table.Tbody>
        </Table>
    )
}