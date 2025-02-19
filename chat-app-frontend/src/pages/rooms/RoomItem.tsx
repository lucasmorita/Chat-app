import { useNavigate } from "react-router";
import { RoomDto } from "../../types/RoomSchema";

const RoomItem = ({ room }: { room: RoomDto }) => {
    const navigate = useNavigate();
    return (
        <div className="flex flex-col gap-4">
            <h2 className="text-center text-xl">{room.name}</h2>
            <p>{room.description}</p>
            <a type="button" className="btn w-16 place-self-center" onClick={() => navigate(`/rooms/${room.id}`)}>Join</a>
        </div>
    );
};

export default RoomItem;