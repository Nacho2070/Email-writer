
const buttonSend = document.querySelector('#btnSend');
const textAreaResponse = document.getElementById("response");

const fetchResponse = async (body) => {

    try {
        const response = await fetch("http://localhost:8080/api/email/generate", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body),
        });

        if (!response.ok) {
            throw new Error("Error en la solicitud: " + response.statusText);
        }

        return await response.text();

    }catch(err) {
        console.log(err);
    }

}

buttonSend.addEventListener("click", async () => {
    const mailContent = document.getElementById("textAreaInput").value

    if(mailContent == null || mailContent === "" ){
        return
    }

    const tone = document.getElementById("toneSelection").value

    const response =  await fetchResponse(buildBody(mailContent, tone))



    if (textAreaResponse) {

        textAreaResponse.value = response;
    }


});

const copyContent = async () => {
    try {
        await navigator.clipboard.writeText(textAreaResponse.value);
        console.log('Content copied to clipboard');
    } catch (err) {
        console.error('Failed to copy: ', err);
    }
}

const buildBody = (mailContent,tone)=>{
    let body = {
        mailContent: mailContent,
        tone : tone
    }

    return body;
}