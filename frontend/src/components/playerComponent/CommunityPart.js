import React, {useEffect} from "react";
import MessageStore from './MessageStore'


let Messages = [];



const CommunityPart = () => {
    const[message, setMessage] = React.useState('');
    const[activeIndex, setActiveIndex] = React.useState(0);


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
            Messages.push(message);//warning на ключи
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
                <MessageStore MessageHolder = {Messages}/>
                </div>
            <div className="enter-button">
                <input type="text" placeholder="Что же это а?.." className="chatInput" value={message} onChange={newMessage} onKeyUp={checkButton}></input>
                <img className="send-button" src={activeIndex === 0 ?"gameField/send.png" : "gameField/sendActive.png"} onClick={sendClick}/>
            </div>
        </div>
    )
}
export default CommunityPart;