import { createContext, useState } from 'react';
import './App.css';
import ChatContainer from './ChatContainer';

export const UserContext = createContext<{ nickname: string | null }>({
  nickname: null,
});

function App() {
  const [finalNickname, setFinalNickname] = useState<string | null>(null);
  const [nickname, setNickname] = useState<string>('');

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(event.target.value);
  }

  const handleSubmitNickname = () => {
    setFinalNickname(nickname);
  }

  return (
    <div>
      <UserContext.Provider value={{ nickname: finalNickname }}>
        {finalNickname === null
          ? <div>
            <input onChange={handleInputChange} value={nickname} style={{ height: 28, width: 500, marginBottom: 45 }} placeholder='닉네임' />
            <button onClick={handleSubmitNickname}>설정</button>
          </div>
          : ''}

        {
          finalNickname !== null
            ? <ChatContainer />
            : ''
        }
      </UserContext.Provider>
    </div>
  );
}

export default App;
