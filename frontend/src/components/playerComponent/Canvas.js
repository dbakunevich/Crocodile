import {useOnDraw} from './CustomHooks';
import React, {useEffect, useState} from "react";
import Stomp from "stompjs";
import SockJS from "sockjs-client";

const Canvas = ({CanWidth, CanHeight, color, width, head}) => {

    const [person, setPerson] = useState(1);

    const socket = new SockJS('http://localhost:8080/game');
    socket.onopen = function (){
        console.log('good');
    }

    const {setCanvasRef, onCanvasMouseDown} = useOnDraw(onDraw);
    let stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

    });

    function changePerson(id) {
        setPerson(id);
        stompClient.subscribe('/topic/session/1', function (greeting) {
            console.log("session!")
        });
        stompClient.subscribe('/queue/session', function (gg){
            console.log("ququq");
        });
        stompClient.send('/app/join/1', {}, '{"id":"' + person + '", "username": "first"}');
    }

    function onDraw(ctx, point, prevPoint) {
        if (head) {
            drawLine(prevPoint, point, ctx, color, width);
            stompClient.send('/app/session/draw/',{},'{prevPoint point, ctx, color, width}');
        }
    }

    function length(start, end){
        return Math.sqrt((start.x - end.x)*(start.x - end.x) + (start.y - end.y)*(start.y - end.y));
    }

    function drawLine(start, end, ctx, color, width) {
        start = (start !== null && start !== undefined) ? start : end;
        ctx.beginPath();
        ctx.strokeStyle = color;
        ctx.moveTo(start.x, start.y);
        drawLineCircle(start, end, ctx, color, width);
        ctx.fillStyle = color;
        ctx.fill();
        ctx.stroke();
    }

    function drawLineCircle(start, end, ctx, color, width) {
        const l = length(start, end);
        let i ;
        const dx = (end.x - start.x) / l;
        const dy = (end.y - start.y) / l;
        for (i = 0; i < l; ++i) {
            ctx.arc(start.x + i * dx, start.y + i * dy, width / 2, 0, 2 * Math.PI);
            ctx.strokeStyle = color;
        }
    }

    return(
        <div>
            <canvas
                width={CanWidth}
                height={CanHeight}
                onMouseDown={onCanvasMouseDown}
                style={canvasStyle}
                ref={setCanvasRef}
            />
            <div className="costil-person1" onClick={()=>{changePerson(1)}}> person1</div>
            <div className="costil-person2" onClick={()=>{changePerson(2)}}> person2</div>
        </div>
    );

}

export default Canvas;

const canvasStyle = {
    border: "1px solid white"
}
