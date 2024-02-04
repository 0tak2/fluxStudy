import { useContext, useEffect, useState } from 'react';
import Chat from './Chat';
import ChatInput from './ChatInput';
import { CONSTANTS, ChatDto } from './common';
import { UserContext } from './App';

function ChatContainer() {
    const userContext = useContext(UserContext);

    const [chatList, setChatList] = useState<ChatDto[]>([]);

    const [eventSource, setEventSource] = useState<EventSource | null>(null);

    const [isSseActive, setIsSseActive] = useState(false);

    const getPreData = async () => {
        const current = new Date();
        const before = new Date();
        before.setHours(current.getHours() - 1);

        const response = await fetch(`${CONSTANTS.baseUrl}/chat?start=${before.toISOString()}&end=${current.toISOString()}`);
        const chatListBefore = (await response.json()).data;
        setChatList([
            ...chatListBefore,
            {
                message: userContext.nickname + '님, 채팅에 오신 것을 환영합니다.',
                sender: '관리자',
                receiver: undefined,
                createdAt: undefined,
            },
        ]);

        if (response.status !== 200) {
            console.error('[Chat] Fail to fetch preivious chat data');
        }
    }

    const initSse = () => {
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

            setIsSseActive(false);
        };

        setEventSource(source);
        setIsSseActive(true);

        console.log('EventSource set: ');
        console.log(eventSource);
    }

    useEffect(() => {
        if (eventSource) {
            eventSource.close();
        }

        const initJob = async () => {
            await getPreData();

            if (!isSseActive) {
                initSse();
            }
        }

        initJob();

        return () => {
            // 컴포넌트가 언마운트될 때 EventSource 종료
            if (eventSource) {
                eventSource.close();
            }
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
