import { useState, useEffect } from 'react';
import { RoomItemDto } from '../types/RoomSchema';

interface UseRoomReturnType {
    room: RoomItemDto | null;
    loading: boolean;
    error: string | null;
  }

const useRoom = (roomId: string): UseRoomReturnType => {
    const [room, setRoom] = useState<RoomItemDto | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchRoom = async () => {
            try {
                setLoading(true);
                const response = await fetch(`http://localhost:8080/rooms/${roomId}`, {
                    credentials: 'include'
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch room');
                }
                const data: RoomItemDto = await response.json();
                setRoom(data);
            } catch (err) {
                if (err instanceof Error) {
                    setError(err.message);
                } else {
                    setError('An unknown error occurred');
                }
            } finally {
                setLoading(false);
            }
        };

        fetchRoom();
    }, [roomId]);

    return { room, loading, error };
};

export default useRoom;