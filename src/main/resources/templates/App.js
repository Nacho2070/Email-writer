const buttonSend = document.querySelector('#btnSend');

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

    const responseElement = document.getElementById("response");
    if (responseElement) {
        responseElement.value = response;
    }

});
/*
* "mailContent": "Hola como andas, te escribo ya que me debias la renta",
    "tone": "CASUAL"
* */
const buildBody = (mailContent,tone)=>{
    let body = {
        mailContent: mailContent,
        tone : tone
    }

    return body;
}