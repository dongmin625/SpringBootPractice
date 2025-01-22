// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/articles');
            });
    });
}

//수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    // 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`, { // 백틱으로 수정 API는 백틱
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
        .then(() => {
            alert('수정이 완료되었습니다.'); // 수정된 부분
            location.replace(`/articles/${id}`); // 백틱으로 수정
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {
    const createButton = document.getElementById('create-btn');

    if (createButton) {
        createButton.addEventListener('click', event => {
            // 입력 값 가져오기
            const title = document.getElementById('title').value.trim();
            const content = document.getElementById('content').value.trim();

            // 유효성 검사
            if (!title || !content) {
                alert('제목과 내용을 모두 입력해주세요.');
                return;
            }

            // API 요청
            fetch('/articles', {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ title, content }),
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('등록 요청 실패');
                    }
                    return response.json();
                })
                .then(data => {
                    alert('등록 완료되었습니다.');
                    location.replace('/articles');
                })
                .catch(error => {
                    console.error('등록 중 오류:', error);
                    alert('등록 중 문제가 발생했습니다.');
                });
        });
    } else {
        alert('버튼이 이상함');
        console.error('등록 버튼을 찾을 수 없습니다.');
    }
});
