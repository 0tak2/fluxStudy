import { ChatDto } from './common';

function Chat({
    message, sender, receiver, createdAt,
}: ChatDto) {
    return (
        <div style={{ marginBottom: 12 }}>
            <div style={{ textAlign: 'left' }}>{message}</div>
            <div style={{ fontSize: 10, textAlign: 'right' }}>{sender}</div>
            <div style={{ fontSize: 10, textAlign: 'right' }}>{receiver}</div>
            <div style={{ fontSize: 10, textAlign: 'right' }}>{createdAt ? new Date(createdAt).toISOString() : ''}</div>
        </div>
    );
}

export default Chat;
