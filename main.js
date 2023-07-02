let color = 'white';
let click = false;

function populateBoard(size) {
    let board = document.querySelector(".board");
    let squares = board.querySelectorAll("div");
    squares.forEach((div) => div.remove());
    
    board.style.gridTemplateColumns = "repeat(" + size + ", 1fr)";
    board.style.gridTermplateRows = "repeat(" + size + ", 1fr)";

    let amount = size * size;
    for (let i = 0; i < amount; i++) {
        let square = document.createElement('div');
        square.addEventListener('mouseover', colorSquare);
        square.style.backgroundColor = "black";
        board.insertAdjacentElement("beforeend", square);
    }
} 

function changeSize(input) {

    if (input >= 2 && input <= 300){
        populateBoard(input);
        document.querySelector(".err").textContent = "";
    }else{
        console.log("issue with squares");
        document.querySelector(".err").textContent = "Size must be between 2-300";

    }
    
}

function colorSquare(){
    if (click){
        if (color === "random"){
            this.style.backgroundColor = `hsl(${Math.random() * 360}, 100%, 80%)`;
        } else {
            this.style.backgroundColor = color;
        }

    }
    
    
}

function changeColor(picked){
    color = picked

    if (picked === "random") {
        document.querySelector(".currentColor").style.background = `linear-gradient(
            90deg,
            rgba(255, 0, 0, 1) 0%,
            rgba(255, 154, 0, 1) 10%,
            rgba(208, 222, 33, 1) 20%,
            rgba(79, 220, 74, 1) 30%,
            rgba(63, 218, 216, 1) 40%,
            rgba(47, 201, 226, 1) 50%,
            rgba(28, 127, 238, 1) 60%,
            rgba(95, 21, 242, 1) 70%,
            rgba(186, 12, 248, 1) 80%,
            rgba(251, 7, 217, 1) 90%,
            rgba(255, 0, 0, 1) 100%
          )`;
    }else{
        document.querySelector(".currentColor").style.background = picked;

    }
}

function resetBoard(){
    let board = document.querySelector(".board");
    let squares = board.querySelectorAll("div");
    squares.forEach((div) => div.style.backgroundColor="black");
}

function randomizeBoard(){
    let board = document.querySelector(".board");
    let squares = board.querySelectorAll("div");
    squares.forEach((div) => div.style.backgroundColor=`hsl(${Math.random() * 360}, 100%, 80%)`);
}
    


document.querySelector("body").addEventListener("click", (e)=> {
    if(e.target.tagName != 'BUTTON'){
        click=!click;
        if (click) {
            document.querySelector(".mode").textContent = "Pen: ON!";
        }else{
            document.querySelector(".mode").textContent = "Pen: Off :(";
        }
    }
    
})


populateBoard(50);

