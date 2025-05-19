import {
    createMultipleChoiceQuestion,
    createCheckboxQuestion,
    createShortAnswerQuestion,
    createLongAnswerQuestion
} from './survey-template.js';

document.addEventListener('DOMContentLoaded', function () {
    const questionsContainer = document.getElementById('questions-container');
    const addQuestionBtn = document.querySelector('.add-question-btn');
    let questionCount = 1;

    // 옵션 추가/삭제
    document.addEventListener('click', function (e) {
        const addOptionBtn = e.target.closest('.add-option');
        if (addOptionBtn) {
            const container = addOptionBtn.closest('.options-container');
            const containerId = container.id;
            const match = containerId.match(/options-container-(\d+)/);
            if (!match) return;
            const qIndex = match[1];

            // 현재 질문 박스 내의 select 값 확인
            const questionBox = container.closest('.question-box');
            const typeSelect = questionBox.querySelector('.form-select');
            const questionType = typeSelect?.value || 'MULTIPLE_CHOICE';

            const labelClass = (questionType === 'CHECKBOX') ? 'checkbox-label' : 'radio-label';
            const inputType = (questionType === 'CHECKBOX') ? 'checkbox' : 'radio';

            const options = container.querySelectorAll('.option:not(.add-option)');
            const nextOptionIndex = options.length;

            const newOption = document.createElement('div');
            newOption.classList.add('option');
            newOption.innerHTML = `
            <label class="${labelClass}">
                <input type="${inputType}" disabled>
                <input type="text" class="text-input"
                    name="questions[${qIndex}].options[${nextOptionIndex}].optionText"
                    value="옵션 ${nextOptionIndex + 1}">
            </label>
            <button class="delete-btn"><i class="fas fa-trash"></i></button>
        `;
            container.insertBefore(newOption, addOptionBtn);
            updateDeleteButtons(container);
            reindexOptions(container, qIndex);
        }

        const deleteBtn = e.target.closest('.delete-btn');
        if (deleteBtn) {
            const optionDiv = deleteBtn.closest('.option');
            const container = deleteBtn.closest('.options-container');
            const containerId = container.id;
            const match = containerId.match(/options-container-(\d+)/);
            const qIndex = match?.[1];

            optionDiv.remove();
            updateDeleteButtons(container);
            if (qIndex) {
                reindexOptions(container, qIndex); // 🔁 여기서도 호출
            }
        }
    });
    // 옵션 삭제 버튼 활성화 여부 수정
    function updateDeleteButtons(container) {
        const options = container.querySelectorAll('.option:not(.add-option)');
        const shouldShow = options.length > 1;

        options.forEach(opt => {
            const btn = opt.querySelector('.delete-btn');
            if (btn) {
                btn.classList.toggle('hidden', !shouldShow);
            }
        });
    }
    // 옵션 인덱스 재정렬
    function reindexOptions(container, questionIndex) {
        const optionInputs = container.querySelectorAll('.option:not(.add-option)');
        optionInputs.forEach((optDiv, newIndex) => {
            const textInput = optDiv.querySelector('input[type="text"]');
            if (textInput) {
                textInput.name = `questions[${questionIndex}].options[${newIndex}].optionText`;
            }
        });
    }

    // 질문 추가
    if (addQuestionBtn) {
        addQuestionBtn.addEventListener('click', function () {
            const newQuestion = createMultipleChoiceQuestion(questionCount++);
            questionsContainer.insertAdjacentHTML('beforeend', newQuestion);
            updateDeleteQuestionVisibility();
        });
    }

    // 질문 삭제 버튼 처리
    const deleteQuestionBtn = document.querySelector('.delete-question-btn');

    function updateDeleteQuestionVisibility() {
        const questionBoxes = document.querySelectorAll('.question-box');
        if (deleteQuestionBtn) {
            deleteQuestionBtn.classList.toggle('hidden', questionBoxes.length <= 1);
        }
    }

    // 질문 삭제 이벤트
    deleteQuestionBtn?.addEventListener('click', function () {
        const questionBoxes = document.querySelectorAll('.question-box');
        if (questionBoxes.length <= 1) return;

        // 마지막 질문 제거
        const lastBox = questionBoxes[questionBoxes.length - 1];
        lastBox.remove();
        questionCount--;
        updateDeleteQuestionVisibility();
    });

    // 질문 유형 변경 시 question-box 교체
    document.addEventListener('change', function (e) {
        const select = e.target.closest('.form-select');
        if (!select) return;

        const questionBox = select.closest('.question-box');
        if (!questionBox) return;

        const indexMatch = select.name.match(/questions\[(\d+)\]/);
        if (!indexMatch) return;

        const index = parseInt(indexMatch[1]);
        let newHTML = '';

        switch (select.value) {
            case 'MULTIPLE_CHOICE':
                newHTML = createMultipleChoiceQuestion(index);
                break;
            case 'CHECKBOX':
                newHTML = createCheckboxQuestion(index);
                break;
            case 'SHORT_ANSWER':
                newHTML = createShortAnswerQuestion(index);
                break;
            case 'LONG_ANSWER':
                newHTML = createLongAnswerQuestion(index);
                break;
        }

        // 실제 교체
        if (newHTML) {
            const wrapper = document.createElement('div');
            wrapper.innerHTML = newHTML;
            const newNode = wrapper.firstElementChild;
            questionsContainer.replaceChild(newNode, questionBox);
        }
    });
    // 공개/비공개에 따라 비밀번호 입력 필드 활성화 토글
    const publicRadio = document.querySelector('input[name="privacy"][value="PUBLIC"]');
    const privateRadio = document.querySelector('input[name="privacy"][value="PRIVATE"]');
    const passwordInput = document.getElementById('password');

    function updatePasswordField() {
        if (privateRadio.checked) {
            passwordInput.disabled = false;
        } else {
            passwordInput.disabled = true;
            passwordInput.value = ''; // 필요시 초기화
        }
    }

    // 초기 상태 반영
    updatePasswordField();

    // 이벤트 리스너 등록
    publicRadio?.addEventListener('change', updatePasswordField);
    privateRadio?.addEventListener('change', updatePasswordField);
});