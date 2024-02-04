import { ChatDto } from './common';

const isoStringToKoreanDatetime = (isoString: string | undefined) => {
    if (!isoString) {
        return '';
    }

    const date = new Date(isoString);
    date.setHours(date.getHours() + 9);
    return `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`
}

function Chat({
    message, sender, receiver, createdAt,
}: ChatDto) {
    return (
        <div style={{ marginBottom: 12 }}>
            <div style={{ textAlign: 'left' }}>{message}</div>
            <div style={{ fontSize: 10, textAlign: 'right' }}>{sender}</div>
            <div style={{ fontSize: 10, textAlign: 'right' }}>{receiver}</div>
            <div style={{ fontSize: 10, textAlign: 'right' }}>{isoStringToKoreanDatetime(createdAt)}</div>
        </div>
    );
}

export default Chat;
