import { useNavigate, useParams } from "react-router";
import useRoom from "../../../hooks/useRoom";
import { RoomDto } from "../../../types/RoomSchema";
import Chat from "./Chat";
import { useContext } from "react";
import { AuthContext } from "../../../components/context/AuthContext";

const RoomChat: React.FC = () => {
    const { roomId } = useParams<{ roomId: string }>();
    const { authed } = useContext(AuthContext);
    const navigate = useNavigate();
    const { room, loading }: { room: RoomDto | null, loading: boolean, error: string | null } = useRoom(roomId!);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!room) {
        return <div>No Rooms available</div>;
    }
    if (!authed) {
        return <>{navigate("/login")}</>
    }
    return (
        <div>
            {roomId && <Chat roomId={roomId} roomName={room.name} />}
        </div>
    );
};

export default RoomChat;
