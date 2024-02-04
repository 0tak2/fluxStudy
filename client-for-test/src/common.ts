interface ChatDto {
    message: string,
    sender: string,
    receiver: string | undefined,
    createdAt: string | undefined,
}

const CONSTANTS = {
    baseUrl: import.meta.env.VITE_BASE_URL,
}

export type { ChatDto };
export { CONSTANTS };
