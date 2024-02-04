import { useState } from "react";

function ChatInput({ onSend }: { onSend: Function }) {
    const [content, setContent] = useState('');

    const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        setContent(event.target.value);
    }

    const handleKeyDown = (event: React.KeyboardEvent<HTMLTextAreaElement>) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            onSend(content);
            setContent('');
        }
    }

    return (
        <textarea style={{ width: 600 }} onChange={handleChange} value={content} onKeyDown={handleKeyDown} />
    );
}

export default ChatInput;
