import { useEffect, useState } from "react";
import { RoomDto, RoomSchema } from '../../types/RoomDto';
import RoomItem from "./RoomItem";

const Room: React.FC = () => {
    const [rooms, setRooms] = useState<RoomDto[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch(`${import.meta.env.VITE_SERVER_HOST}/rooms`,
            { credentials: 'include' }
        )
            .then(response => response.json())
            .then(response => {
                console.log(response);
                const parsedData = RoomSchema.array().safeParse(response);
                if (!parsedData.success) {
                    console.error('Failed to parse the response data', parsedData.error);
                    return;
                }
                setRooms(parsedData.data);
            })
            .catch(error => {
                setError('Failed to fetch the rooms');
                console.error('There was an error fetching the rooms!', error);
            });
    }, []);
    return (
        <div>
            <h1 className="text-2xl text-center mb-4">Available Rooms</h1>
            <div className="grid justify-center grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
                {rooms.length === 0 ? <p className="text-center">No rooms available</p> :
                    rooms.map(room => (
                        <div key={room.id} className="card p-8 bg-base-300 shadow-xl min-w-[300px] mx-auto">
                            <RoomItem room={room} />
                        </div>
                    ))}
                {error && <p>{error}</p>}
            </div>
        </div>
    );
};

export default Room;