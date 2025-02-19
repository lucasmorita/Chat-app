import { useState } from "react";


const CreateRoomButton: React.FC = () => {
    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [loading, setLoading] = useState<boolean | null>(null);
    const handleCreateRoom = () => {
        setLoading(true);
        fetch(`${import.meta.env.VITE_SERVER_HOST}/rooms`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: name,
                description: description
            }),
            credentials: 'include'
        }).then(res => {
            console.log(res);
        }).catch(err => {
            console.error(err);
        });
        setLoading(false);
    }
    return (
        <div>
            <button className="btn" onClick={() => (document.getElementById('create-room-modal') as HTMLDialogElement)!.showModal()}>New Room</button>
            <RoomModal
                isLoading={loading}
                handleName={setName}
                handleDescription={setDescription}
                handleCreateRoom={handleCreateRoom}
            />
        </div>
    );
};

type RoomModalInfo = {
    handleName: React.Dispatch<React.SetStateAction<string>>,
    handleDescription: React.Dispatch<React.SetStateAction<string>>,
    handleCreateRoom: () => void,
    isLoading: boolean | null
}

const RoomModal: React.FC<RoomModalInfo> = ({ handleName, handleDescription, handleCreateRoom }) => {
    return (
        <dialog id="create-room-modal" className="modal">
            <div className="modal-box">
                <h3 className="font-bold text-lg">Room information</h3>
                {/* <p className="py-4">Press ESC key or click the button below to close</p> */}
                <div className="py-4">
                    <label htmlFor="room-name" className="floating-label">
                        <span>Name</span>
                        <input name="room-name" type="text" placeholder="Name" className="input w-full" onChange={(e) => { handleName(e.target.value) }} />
                    </label>
                </div>
                <div className="py-4">
                    <label htmlFor="room-description" className="floating-label">
                        <span className="floating-label">Description (optional)</span>
                        <input name="room-description" type="text" placeholder="Description" className="input w-full" onChange={(e) => { handleDescription(e.target.value) }} />
                    </label>

                </div>
                <div className="modal-action">
                    <form method="dialog">
                        {/* if there is a button in form, it will close the modal */}
                        <button className="btn" onClick={() => handleCreateRoom()}>Create</button>
                    </form>
                </div>
            </div>
        </dialog>
    );
}

export default CreateRoomButton;