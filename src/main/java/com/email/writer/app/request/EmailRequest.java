package com.email.writer.app.request;

public record EmailRequest(
        String mailContent,
        Tone tone
) {}
