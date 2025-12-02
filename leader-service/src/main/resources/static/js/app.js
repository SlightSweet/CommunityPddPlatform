function loadLeaders() {
    fetch('/leader/findAllLeaders') // 调用后端接口
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById('leaderList');
            list.innerHTML = '';
            data.forEach(leader => {
                const li = document.createElement('li');
                li.textContent = `${leader.id} - ${leader.name} - ${leader.phone} - ${leader.community}`;
                list.appendChild(li);
            });
        })
        .catch(err => console.error(err));
}
