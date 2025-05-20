export function renderSurveyCharts(surveyData) {
    surveyData.forEach((question, index) => {
        if (!question.options) return;

        const labels = question.options.map(o => o.optionText);
        const data = question.options.map(o => o.count);

        const ctxId = question.type === 'MULTIPLE_CHOICE'
            ? `chart-pie-${index}`
            : `chart-bar-${index}`;

        const ctx = document.getElementById(ctxId);
        if (!ctx) return;

        const isPieChart = question.type === 'MULTIPLE_CHOICE';

        new Chart(ctx, {
            type: question.type === 'MULTIPLE_CHOICE' ? 'pie' : 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: '응답 수',
                    data: data,
                    backgroundColor: [
                        '#f94144', '#f3722c', '#f8961e', '#f9c74f',
                        '#90be6d', '#43aa8b', '#577590', '#277da1'
                    ],
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: isPieChart,
                        position: 'bottom'
                    }
                }
            }
        });
    });
}