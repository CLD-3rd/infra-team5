document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".survey-form");

    if (form) {
        form.addEventListener("submit", (e) => {
            if (!validateRequiredQuestions()) {
                e.preventDefault();
            }
        });
    }
});

function validateRequiredQuestions() {
    const requiredBoxes = document.querySelectorAll(".required-question");

    for (let box of requiredBoxes) {
        const inputs = box.querySelectorAll("input[type='radio'], input[type='checkbox'], input[type='text'], textarea");
        let isAnswered = false;

        for (let input of inputs) {
            if ((input.type === 'radio' || input.type === 'checkbox') && input.checked) {
                isAnswered = true;
                break;
            }
            if ((input.type === 'text' || input.tagName === 'TEXTAREA') && input.value.trim() !== "") {
                isAnswered = true;
                break;
            }
        }

        if (!isAnswered) {
            box.scrollIntoView({behavior: 'smooth', block: 'center'});
            box.classList.add('unanswered-highlight');
            alert("필수 질문을 모두 답해주세요.");
            return false;
        }
    }

    return true;
}