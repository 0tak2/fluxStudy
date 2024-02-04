import { useContext, useEffect, useState } from 'react';
import Chat from './Chat';
import ChatInput from './ChatInput';
import { CONSTANTS, ChatDto } from './common';
import { UserContext } from './App';

function ChatContainer() {
    const userContext = useContext(UserContext);

    const [chatList, setChatList] = useState<ChatDto[]>([
        {
            message: userContext.nickname + '님, 채팅에 오신 것을 환영합니다.',
            sender: '관리자',
            receiver: undefined,
            createdAt: new Date().toISOString(),
        },
    ]);

    const [eventSource, setEventSource] = useState<EventSource | null>(null);

    useEffect(() => {
        const source = new EventSource(CONSTANTS.baseUrl + '/sse');

        source.onopen = () => {
            // 연결 시 할 일
            console.log('[SSE] SSE 연결됨');
        };

        source.addEventListener('new-chat', (event) => {
            console.log('[SSE] 새로운 메시지');

            const parsedData = JSON.parse(event.data);
            setChatList((prevChatList) => [
                ...prevChatList,
                parsedData,
            ]);
        });

        source.onerror = (error) => {
            // 종료 또는 에러 발생 시 할 일
            source.close();

            console.error(`[SSE] 에러 발생${error}`);

            // if (error.target.readyState === EventSource.CLOSED) {
            //     // 종료 시 할 일
            // }
        };

        setEventSource(source);
        console.log('EventSource set: ');
        console.log(eventSource);

        return () => {
            // 컴포넌트가 언마운트될 때 EventSource 종료
            source.close();
        };
    }, []);

    const handleSend = async (content: string) => {
        const response = await fetch(CONSTANTS.baseUrl + '/chat', {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                message: content,
                sender: userContext.nickname,
            }),
        });

        if (response.status !== 200) {
            console.error('[Chat] Fail post new chat');
        }
    }

    return (
        <div style={{ width: 600 }}>
            <div>
                {
                    chatList.map((chat, idx) => (
                        <Chat
                            key={idx}
                            message={chat.message}
                            sender={chat.sender}
                            receiver={chat.receiver}
                            createdAt={chat.createdAt}
                        />
                    ))
                }
            </div>
            <div>
                <ChatInput onSend={handleSend} />
            </div>
        </div>
    );
}

export default ChatContainer;
