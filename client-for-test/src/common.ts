interface ChatDto {
    message: string,
    sender: string,
    receiver: string | undefined,
    createdAt: string | undefined,
}

export type { ChatDto };
