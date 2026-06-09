type EventInfoData = {
    name: string,
    description: string
}

export function EventInfo(
    { 
        name,
        description
    }: EventInfoData
) {
    return <>
        <h1>{name}</h1>
        <h2>{description}</h2>
    </>
}