package com.storiesteller.intronotes

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.storiesteller.intronotes.persistence.repository.DynamoIntroNotesRepository
import com.storiesteller.intronotes.read.readNotes
import com.storiesteller.sdk.serialization.json.storiesTellerJson

class ReadIntroNotes : RequestHandler<Unit, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: Unit, context: Context): APIGatewayProxyResponseEvent {
        val response = readNotes(
            json = storiesTellerJson,
            provideNote = {
                DynamoIntroNotesRepository.readNote("87eb231f-c263-4562-9d86-ce980bf954a7")
            }
        )

        return APIGatewayProxyResponseEvent()
            .withStatusCode(200)
            .withIsBase64Encoded(false)
            .withBody(response)
    }
}

