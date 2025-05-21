function endSurvey(id) {
    if (confirm('설문을 종료하시겠습니까?')) {
        fetch('/survey/' + id + '/close', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(response => {
            if (response.ok) {
                alert('설문이 종료되었습니다.');
                location.reload();
            } else {
                alert('설문 종료에 실패했습니다.');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('설문 종료 중 오류가 발생했습니다.');
        });
    }
}

function viewResults(id) {
    location.href = '/surveys/' + id + '/result';
}

function deleteSurveys() {
    const selectedSurveys = document.querySelectorAll('input[name="selectedSurveys"]:checked');

    if (selectedSurveys.length === 0) {
        alert('삭제할 설문을 선택해주세요.');
        return;
    }

    if (confirm('선택한 설문을 삭제하시겠습니까?')) {
        const surveyIds = Array.from(selectedSurveys).map(checkbox => checkbox.value);

        fetch('/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ids: surveyIds})
        }).then(response => {
            if (response.ok) {
                alert('선택한 설문이 삭제되었습니다.');
                location.reload();
            } else {
                alert('설문 삭제에 실패했습니다.');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('설문 삭제 중 오류가 발생했습니다.');
        });
    }
}