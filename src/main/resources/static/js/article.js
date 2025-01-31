document.addEventListener('DOMContentLoaded', () => {
    // 삭제 기능 (delete-btn)
    const deleteButton = document.getElementById('delete-btn');

    if (deleteButton) {
        deleteButton.addEventListener('click', event => {
            let id = document.getElementById('article-id').value;
            fetch(`/api/articles/${id}`, {
                method: 'DELETE'
            })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/articles');  // 글 목록으로 리다이렉트
            });
        });
    }

    // 새 글 등록 (create-btn)
    const createButton = document.getElementById('create-btn');

    if (createButton) {
        createButton.addEventListener('click', event => {
            const title = document.getElementById('title').value.trim();
            const content = document.getElementById('content').value.trim();

            // 유효성 검사
            if (!title || !content) {
                alert('제목과 내용을 모두 입력해주세요.');
                return;
            }

            // API 요청
            fetch('/api/articles', {
                method: 'POST',  // 글 등록을 위한 POST 요청
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
                location.replace('/articles');  // 글 목록 페이지로 리다이렉트
            })
            .catch(error => {
                console.error('등록 중 오류:', error);
                alert('등록 중 문제가 발생했습니다.');
            });
        });
    }

    // 수정 기능 (modify-btn)
    const modifyButton = document.getElementById('modify-btn');

    if (modifyButton) {
        modifyButton.addEventListener('click', event => {
            let id = document.getElementById('article-id').value;

            fetch(`/api/articles/${id}`, {  // 글 수정을 위한 PUT 요청
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
                alert('수정이 완료되었습니다.');
                location.replace(`/articles/${id}`);  // 수정된 글 보기 페이지로 리다이렉트
            });
        });
    }
});

