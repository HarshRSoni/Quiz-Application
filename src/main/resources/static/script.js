let currentQuestionIndex = 0;
let score = 0;
let questions = [];
let timer;
const timePerQuestion = 10;
let quizStarted = false;

//event listener for buttons
document.getElementById('start-btn').addEventListener('click', () => {
    if (!quizStarted) {
        quizStarted = true;
        console.log('Start button clicked');
        fetchQuizData();
    }
});

document.getElementById('next-btn').addEventListener('click', () => {
    console.log('Next button clicked');
    nextQuestion();
});
document.getElementById('again').addEventListener('click', () => {
    window.location.href = 'intro.html.html';
});


async function fetchQuizData() {
    const selectedCategoryId = localStorage.getItem('selectedCategoryId');
    const selectedDifficulty = localStorage.getItem('selectedDifficulty');

    if (selectedCategoryId && selectedDifficulty) {
        try {
            const response = await fetch(`http://localhost:8080/questions?categoryIds=${selectedCategoryId}&difficulty=${selectedDifficulty}`);
            const data = await response.json();
            console.log('API response:', data);

            if (data.length > 0) {
                questions = data;

                document.getElementById('start-btn').style.display = 'none';
                startQuiz();
            } else {
                console.error('No questions found in API response:', data);
                alert('No questions found. Please try again later.');
            }
        } catch (error) {
            console.error('Error fetching quiz data:', error);
            alert('Error fetching quiz data. Please try again later.');
        }
    } else {
        alert('Category and difficulty not set in local storage.');
    }
}

function startQuiz() {
    console.log('Starting quiz');
    currentQuestionIndex = 0; // Reset currentQuestionIndex when starting a new quiz
    score = 0; // Reset score
    document.getElementById('score-value').textContent = score;
    document.getElementById('next-btn').style.display = 'none';
    displayQuestion();
}

function displayQuestion() {
    clearInterval(timer);

    if (currentQuestionIndex < questions.length) {
        const currentQuestion = questions[currentQuestionIndex];
        document.getElementById('question').textContent = currentQuestion.question;
        console.log('Displaying question:', currentQuestionIndex);
        document.getElementById('options').innerHTML = '';

        ['optionA', 'optionB', 'optionC', 'optionD'].forEach(optionKey => {
            if (currentQuestion[optionKey]) {
                addOption(currentQuestion[optionKey], currentQuestion.answer === currentQuestion[optionKey]);
            }
        });

        startTimer();
    } else {
        console.log('End of Quiz');
        endQuiz();
    }
}

function addOption(option, isCorrect) {
    const optionElement = document.createElement('div');
    optionElement.classList.add('option');
    optionElement.textContent = option;
    optionElement.addEventListener('click', () => {
        handleAnswer(isCorrect);
    });
    document.getElementById('options').appendChild(optionElement);
}

function handleAnswer(isCorrect) {
    clearInterval(timer);
    const feedbackElement = document.getElementById('feedback');
    feedbackElement.textContent = '';
    if (isCorrect) {
        score++;
    } else {
        const currentQuestion = questions[currentQuestionIndex];
        feedbackElement.textContent = 'Wrong! Correct Answer is ' + currentQuestion.answer;
    }
    document.getElementById('score-value').textContent = score;
    document.getElementById('next-btn').style.display = 'block';
}

function nextQuestion() {
    console.log('Moving to next question');
    currentQuestionIndex++;
    if (currentQuestionIndex < questions.length) {
        displayQuestion();
        document.getElementById('feedback').textContent = '';
        document.getElementById('next-btn').style.display = 'none';
    } else {
        endQuiz();
    }

}

function endQuiz() {
    clearInterval(timer);

    document.getElementById('question').textContent = 'Quiz completed!';
    document.getElementById('options').innerHTML = '';
    document.getElementById('feedback').textContent = `Your Final Score is: ${score}/${questions.length}`;
    document.getElementById('next-btn').style.display = 'none';
    document.getElementById('timer').style.display = 'none';
    document.getElementById('score').style.display = 'none';
    document.getElementById('again').style.display = 'block';

    quizStarted = false;
}

function startTimer() {
    clearInterval(timer);
    let timeLeft = timePerQuestion;
    const timerElement = document.getElementById('timer');
    timerElement.style.display = 'block'; // Ensure the timer is visible
    timerElement.textContent = `Time left: ${formatTime(timeLeft)} sec`; // Display initial time

    timer = setInterval(() => {
        timeLeft--;
        timerElement.textContent = `Time left: ${ formatTime(timeLeft) } sec`; // Update time display
        if (timeLeft <= 0) {
            clearInterval(timer); // Stop the timer when time runs out
            handleAnswer(false); // Mark the answer as incorrect when time runs out
        }
    }, 1000);
}

function formatTime(seconds) {
    return seconds;
}
