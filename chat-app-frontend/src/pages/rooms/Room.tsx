import { useEffect, useState } from "react";
import { RoomItemDto, RoomsResponseSchema } from '../../types/RoomSchema';
import RoomItem from "./RoomItem";
import CreateRoomButton from "../../components/CreateRoomButton";
import Loading from "../../components/Loading";

const Room: React.FC = () => {
    const [rooms, setRooms] = useState<RoomItemDto[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        fetch(`${import.meta.env.VITE_SERVER_HOST}/rooms`,
            { credentials: 'include' }
        )
            .then(response => response.json())
            .then(response => {
                console.log(response);
                const parsedData = RoomsResponseSchema.safeParse(response);
                if (!parsedData.success) {
                    console.error('Failed to parse the response data', parsedData.error);
                    return;
                }
                setRooms(prev => prev.concat(parsedData.data.rooms));
                setTimeout(() => {
                    setLoading(false);
                }, 2000);
            })
            .catch(error => {
                setError('Failed to fetch the rooms');
                console.error('There was an error fetching the rooms!', error);
            });
    }, []);
    return (
        <div className="h-[80vh]">
            <div className="flex justify-center items-center gap-4">
                <h1 className="text-2xl text-center my-4">Rooms</h1>
                <CreateRoomButton />
            </div>
            {
                loading
                    ? <Loading />
                    : rooms.length === 0
                        ? <div className="h-full flex justify-center items-center"><p className="text-center">No rooms available</p></div>
                        : <div className="grid justify-center grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
                            {rooms.map(room => (
                                <div key={room.id} className="card p-8 bg-base-300 shadow-xl min-w-[300px] mx-auto">
                                    <RoomItem room={room} />
                                </div>
                            ))}
                        </div>
            }
            {error && <p>{error}</p>}

        </div>
    );
};

export default Room;