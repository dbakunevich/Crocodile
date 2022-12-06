import React, { useState} from "react";
import MessageList from './MessageList'




const CommunityPart = () => {
    const [Messages, setMessages] = useState([])
    const[message, setMessage] = useState('');
    const[activeIndex, setActiveIndex] = useState(0);
/*
    useEffect(()=>{
        const raw = localStorage.getItem('messages') || []
        setMessages(JSON.parse(raw));
    }, [])


    useEffect(()=> {
        localStorage.setItem('messages', JSON.stringify(Messages));
    }, [Messages])

*/
    const addMessage = () => {
        setMessages([
            ...Messages,
            {
                id: Messages.length,
                text: message,
                liked: false,
                disliked: false
            }
        ])
    }


    const newMessage = (e) => {
        let a = e.target.value;
        setMessage(a)
        if (a !== '')
            setActiveIndex(1);
        else
            setActiveIndex(0);
    }

    const sendClick = () => {
        if (message !== '') {
            addMessage()//warning на ключи
            setMessage('');
            setActiveIndex(0);
        }
        else
            console.log("nothing");
    }

    const checkButton = (e) => {
        if (e.code === "Enter")
            sendClick();
    }


    return (
        <div className="chat">
            <div className="text-field">
                <MessageList Messages={Messages}/>
            </div>
            <div className="enter-button">
                <input type="text" placeholder="Что же это а?.." className="chatInput" value={message} onChange={newMessage} onKeyUp={checkButton}></input>
                <img className="send-button" src={activeIndex === 0 ?"gameField/send.png" : "gameField/sendActive.png"} onClick={sendClick} alt={"no"}/>
            </div>
        </div>
    )
}
export default CommunityPart