# 📬 API 명세

본 문서는 Surbee 설문조사 웹 서비스의 API 엔드포인트 명세를 설명합니다.

---

## 📌 메인 페이지

### ✅ 설문 전체 목록 조회
- **Method**: GET
- **URL**: `/`
- **Request**: 없음
- **Response**:
```json
{
  "activeSurveys": [...],
  "popularSurveys": [...],
  "closedSurveys": [...]
}
```
- **설명**: 진행 중인 설문, 인기 설문, 종료된 설문을 구분하여 반환합니다.

---

## 📝 설문 생성

### ✅ 설문 생성 요청
- **Method**: POST
- **URL**: `/surveys/create`
- **Request Body**:
```json
{
  "title": "가장 좋아하는 계절은?",
  "description": "계절 선호도 설문입니다.",
  "isPublic": true,
  "password": "optional123",
  "questions": [
    {
      "questionText": "당신이 가장 좋아하는 계절은?",
      "type": "SINGLE",
      "isRequired": true,
      "options": [
        { "optionText": "봄" },
        { "optionText": "여름" },
        { "optionText": "가을" },
        { "optionText": "겨울" }
      ]
    },
    {
      "questionText": "선호하는 이유는 무엇인가요?",
      "type": "TEXT",
      "isRequired": false,
      "options": []
    }
  ]
}
```
- **Response**: 생성된 설문 정보

---

## ✅ 설문 참여

### ✅ 특정 설문 문항 조회
- **Method**: GET
- **URL**: `/surveys/{surveyId}`
- **Response**:
```json
{
  "survey": {
    "surveyId": 101,
    "title": "나의 최애 계절은?",
    "questions": [
      {
        "question_id": 1,
        "question_text": "가장 좋아하는 계절은?",
        "question_type": "SINGLE",
        "options": [
          { "option_id": 1, "option_text": "봄" },
          { "option_id": 2, "option_text": "여름" }
        ]
      }
    ]
  }
}
```

### ✅ 설문 응답 제출
- **Method**: POST
- **URL**: `/surveys/{surveyId}/answer`
- **Request Body**:
```json
{
  "surveyId": 101,
  "answers": [
    {
      "questionId": 1,
      "optionId": 3
    },
    {
      "questionId": 2,
      "answerText": "가을이 좋아서요."
    }
  ]
}
```

---

## 📊 설문 결과 조회

### ✅ 설문 결과 반환
- **Method**: GET
- **URL**: `/surveys/{surveyId}/result`
- **Response**:
```json
{
  "survey_id": 101,
  "submission_count": 87,
  "questions": [
    {
      "question_id": 1,
      "question_text": "가장 좋아하는 계절은?",
      "options": [
        { "option_text": "봄", "count": 10 },
        { "option_text": "가을", "count": 38 }
      ]
    }
  ]
}
```

---

## 👤 마이페이지

### ✅ 설문 목록 조회
- **Method**: GET
- **URL**: `/user/mypage`
- **Response**:
```json
{
  "surveys": [
    {
      "id": 101,
      "title": "나의 최애 계절은?",
      "isPublic": true,
      "isClosed": false,
      "submissionCount": 87,
      "createdAt": "2025-05-20T10:15:30"
    }
  ]
}
```

---

## ⚙ 기타 기능

### ✅ 설문 종료
- **Method**: POST
- **URL**: `/surveys/{surveyId}/close`

### ✅ 설문 다중 삭제
- **Method**: POST
- **URL**: `/delete`
- **Request Body**:
```json
{
  "ids": [1, 2, 3]
}
```

---

✅ **참고사항**
- 모든 날짜/시간은 ISO 8601 포맷 사용 (예: `2025-05-20T10:15:30`)
- 필수 필드 누락 시 400 Bad Request 반환

