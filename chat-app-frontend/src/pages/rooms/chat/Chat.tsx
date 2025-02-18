import { fromUnixTime, getUnixTime } from "date-fns";
import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import useWebSocket, { ReadyState } from "react-use-websocket";
import { AuthContext } from "../../../components/context/AuthContext";


interface ChatProps {
    roomId: string;
}

type MessageEventType = {
    username: string;
    timestamp: number;
    message: string;
}

const Chat: React.FC<ChatProps> = ({ roomId }) => {
    const [messageHistory, setMessageHistory] = useState<MessageEventType[]>([]);
    const { sendMessage, lastMessage, readyState } = useWebSocket(`ws://localhost:8080/rooms/${roomId}/chat`);
    const { username } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        console.info("useEffect", lastMessage);
        if (lastMessage !== null) {
            const message = JSON.parse(lastMessage.data);
            setMessageHistory(prev => [...prev, message])
        }
    }, [lastMessage]);

    useEffect(() => {
        console.info("message history:", messageHistory);
    }, [messageHistory]);

    const handleClickSendMessage = () => {
        const text = (document.getElementById("chat") as HTMLTextAreaElement).value;
        const data = {
            message: text,
            username: username,
            timestamp: getUnixTime(new Date())
        };
        console.log(data);
        sendMessage(JSON.stringify(data));
    }

    const connectionStatus = {
        [ReadyState.CONNECTING]: 'Connecting',
        [ReadyState.OPEN]: 'Open',
        [ReadyState.CLOSING]: 'Closing',
        [ReadyState.CLOSED]: 'Closed',
        [ReadyState.UNINSTANTIATED]: 'Uninstantiated',
    }[readyState];
    return (
        <>
            <div className="flex mx-8 gap-4">
                <a type="button" onClick={() => navigate("/rooms")} className="btn btn-neutral">&lt; Back</a>
                <span>The WebSocket is currently {connectionStatus}</span>
            </div>
            <h1 className="text-2xl text-center">Chat</h1>
            <div className="flex flex-col items-center">
                <div className="items-center w-1/2 h-[50vh] overflow-y-scroll">
                    {messageHistory.map((messageEvent: MessageEventType, idx: number) => (
                        messageEvent.username === username ? (
                            <div className="w-full my-2 chat chat-end">
                                <div className="chat-header">
                                    Me
                                    <time className="text-xs opacity-50">{fromUnixTime(messageEvent.timestamp).toLocaleTimeString()}</time>
                                </div>
                                <div key={idx} className="chat-bubble">
                                    {messageEvent.message}
                                </div>
                            </div>
                        ) : (
                            <div className="w-full my-2 chat chat-start">
                                <div className="chat-header">
                                    {messageEvent.username}
                                    <time className="text-xs opacity-50">{fromUnixTime(messageEvent.timestamp).toLocaleTimeString()}</time>
                                </div>
                                <div key={idx} className="chat-bubble">
                                    {messageEvent.message}
                                </div>
                            </div>
                        )
                    ))}
                </div>
                <div className="flex gap-4 items-center">
                    <textarea id="chat" className="textarea bg-base-200 w-80 h-20"></textarea>
                    <button className="btn btn-secondary" onClick={handleClickSendMessage} disabled={readyState !== ReadyState.OPEN}>Send</button>
                </div>
            </div>
        </>
    );
};
export default Chat;