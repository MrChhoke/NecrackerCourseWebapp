function validationProduct() {
    var data = new FormData();
    data.append('name', document.getElementById('name').value);
    data.append('price', document.getElementById('price').value);
    data.append('description', document.getElementById('desc').value);
    data.append('file', document.getElementById('image').files[0]);

    fetch('adminpanel', {
        method: 'post',
        body: data
    }).then((response) => {
        return response.json();
    })
        .then(response => {
            let dangerButton = document.getElementById('buttonDanger');
            if (response.length > 0) {
                dangerButton.style.visibility = "visible";
                let content = document.getElementById('changeLog');
                content.innerText = "";
                for (let message of response) {
                    if (message.hasOwnProperty('defaultMessage')) {
                        let newChild = document.createElement("p");
                        newChild.innerText = message.defaultMessage;
                        content.append(newChild);
                    }
                }
            }
        }).catch(reason => {
        let dangerButton = document.getElementById('buttonDanger');
        dangerButton.style.visibility = 'hidden';
        document.getElementById('name_card_show').innerText = 'The card title';
        document.getElementById('the_description').innerText = 'The description';
        document.getElementById('imageProduct').src = '../static/img/defaulIcon.png';
        document.getElementById('inputGroupSelect01').selectedIndex = 0;
        document.getElementById('price').value = "";
        document.getElementById('desc').value = "";
        document.getElementById('name').value = "";
        document.getElementById('image').value = "";
    });

}
