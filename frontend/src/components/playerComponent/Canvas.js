import {useOnDraw} from './CustomHooks';
import React from "react";



const Canvas = ({CanWidth, CanHeight, color, width, head, stompClient}) => {

    function greed(message){
        let json = JSON.parse(message.body);
        if (json.type === "IMAGE_UPDATE") {
            getCanvasRef();
            let ctx = getCanvasRef();
            drawLine(json.point, json.prevPoint, ctx, json.color, json.width);
        }
    }


    const {setCanvasRef, onCanvasMouseDown, getCanvasRef} = useOnDraw(onDraw);

    function changePerson(id) {
        stompClient.subscribe('/topic/session/1', greed);
        stompClient.subscribe('/user/queue/session', greed);
        stompClient.send('/app/join/1', {}, ('{"id":"' + id + '", "username": "first"}'));
    }

    function onDraw(ctx, point, prevPoint) {
        if (head) {
            drawLine(prevPoint, point, ctx, color, width);
            let json = {"prevPoint": prevPoint, "point": point, "color": color, "width": width};
            stompClient.send('/app/session/draw',{}, JSON.stringify(json));
        }
    }

    function length(start, end){
        return Math.sqrt((start.x - end.x)*(start.x - end.x) + (start.y - end.y)*(start.y - end.y));
    }

    function drawLine(start, end, ctx, color, width) {
        console.log(start, end);
        if (end !== null && end !== undefined) {
            start = (start !== null && start !== undefined) ? start : end;
            ctx.beginPath();
            ctx.strokeStyle = color;
            ctx.moveTo(start.x, start.y);
            drawLineCircle(start, end, ctx, color, width);
            ctx.fillStyle = color;
            ctx.fill();
            ctx.stroke();
        }
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

