import {useOnDraw} from './CustomHooks';

const Canvas = ({CanWidth, CanHeight, color, width}) => {

    const {setCanvasRef, onCanvasMouseDown} = useOnDraw(onDraw);

    function onDraw(ctx, point, prevPoint) {
        drawLine(prevPoint, point, ctx, color, width);
    }

    function length(start, end){
        return Math.sqrt((start.x - end.x)*(start.x - end.x) + (start.y - end.y)*(start.y - end.y));
    }

    function drawLine(start, end, ctx, color, width) {
        start = start ?? end;
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
        <canvas
            width={CanWidth}
            height={CanHeight}
            onMouseDown={onCanvasMouseDown}
            style={canvasStyle}
            ref={setCanvasRef}
        />
    );

}

export default Canvas;

const canvasStyle = {
    border: "1px solid black"
}

