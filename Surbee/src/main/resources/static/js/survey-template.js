export function createMultipleChoiceQuestion(index) {
    return `
    <div class="question-box">
        <div class="question-header">
            <input type="text" class="question-text" name="questions[${index}].questionText" placeholder="질문">
            <div class="question-type">
                <select class="form-select" name="questions[${index}].type">
                    <option value="MULTIPLE_CHOICE" selected>객관식</option>
                    <option value="CHECKBOX">체크박스</option>
                    <option value="SHORT_ANSWER">단답형</option>
                    <option value="LONG_ANSWER">장문형</option>
                </select>
            </div>
        </div>
        <div class="type-description">다중 선택 X</div>
        <div class="options-container" id="options-container-${index}">
            <div class="option">
                <label class="radio-label">
                    <input type="radio" disabled>
                    <input type="text" class="text-input" name="questions[${index}].options[0].optionText" value="옵션 1">
                </label>
                <button class="delete-btn hidden"><i class="fas fa-trash"></i></button>
            </div>
            <div class="option add-option">
                <label class="radio-label">
                    <input type="radio" disabled>
                    <span>옵션 추가</span>
                </label>
            </div>
        </div>
        <div class="question-footer">
            <button class="ai-btn" type="button">AI 응답 생성</button>
            <div class="required-toggle">
                <span>필수</span>
                <label class="switch">
                    <input type="checkbox" name="questions[${index}].isRequired">
                    <span class="slider round"></span>
                </label>
            </div>
        </div>
    </div>`;
}

export function createCheckboxQuestion(index) {
    return `
    <div class="question-box">
        <div class="question-header">
            <input type="text" class="question-text" name="questions[${index}].questionText" placeholder="질문">
            <div class="question-type">
                <select class="form-select" name="questions[${index}].type">
                    <option value="MULTIPLE_CHOICE">객관식</option>
                    <option value="CHECKBOX" selected>체크박스</option>
                    <option value="SHORT_ANSWER">단답형</option>
                    <option value="LONG_ANSWER">장문형</option>
                </select>
            </div>
        </div>
        <div class="type-description">다중 선택 O</div>
        <div class="options-container" id="options-container-${index}">
            <div class="option">
                <label class="checkbox-label">
                    <input type="checkbox" disabled>
                    <input type="text" class="text-input" name="questions[${index}].options[0].optionText" value="옵션 1">
                </label>
                <button class="delete-btn hidden"><i class="fas fa-trash"></i></button>
            </div>
            <div class="option add-option">
                <label class="checkbox-label">
                    <input type="checkbox" disabled>
                    <span>옵션 추가</span>
                </label>
            </div>
        </div>
        <div class="question-footer">
            <button class="ai-btn" type="button">AI 응답 생성</button>
            <div class="required-toggle">
                <span>필수</span>
                <label class="switch">
                    <input type="checkbox" name="questions[${index}].isRequired">
                    <span class="slider round"></span>
                </label>
            </div>
        </div>
    </div>`;
}

export function createShortAnswerQuestion(index) {
    return `
    <div class="question-box">
        <div class="question-header">
            <input type="text" class="question-text" name="questions[${index}].questionText" placeholder="질문">
            <div class="question-type">
                <select class="form-select" name="questions[${index}].type">
                    <option value="MULTIPLE_CHOICE">객관식</option>
                    <option value="CHECKBOX">체크박스</option>
                    <option value="SHORT_ANSWER" selected>단답형</option>
                    <option value="LONG_ANSWER">장문형</option>
                </select>
            </div>
        </div>
        <div class="type-description">최대 50자 이내</div>
        <div class="text-input-container">
            <input type="text" class="text-input" placeholder="단답형 텍스트 예시" readonly>
        </div>
        <div class="question-footer">
            <button class="ai-btn hidden" type="button">AI 응답 생성</button>
            <div class="required-toggle">
                <span>필수</span>
                <label class="switch">
                    <input type="checkbox" name="questions[${index}].isRequired">
                    <span class="slider round"></span>
                </label>
            </div>
        </div>
    </div>`;
}

export function createLongAnswerQuestion(index) {
    return `
    <div class="question-box">
        <div class="question-header">
            <input type="text" class="question-text" name="questions[${index}].questionText" placeholder="질문">
            <div class="question-type">
                <select class="form-select" name="questions[${index}].type">
                    <option value="MULTIPLE_CHOICE">객관식</option>
                    <option value="CHECKBOX">체크박스</option>
                    <option value="SHORT_ANSWER">단답형</option>
                    <option value="LONG_ANSWER" selected>장문형</option>
                </select>
            </div>
        </div>
        <div class="type-description">최소 100자 이상</div>
        <div class="text-input-container">
            <input type="text" class="text-input" placeholder="장문형 텍스트 예시" readonly>
        </div>
        <div class="question-footer">
            <button class="ai-btn hidden" type="button">AI 응답 생성</button>
            <div class="required-toggle">
                <span>필수</span>
                <label class="switch">
                    <input type="checkbox" name="questions[${index}].isRequired">
                    <span class="slider round"></span>
                </label>
            </div>
        </div>
    </div>`;
}