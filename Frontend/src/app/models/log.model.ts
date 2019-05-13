export class Log {
    id: number;
    createdAt: number;
    description: string;
    senderClass: string;
    level: string;

    constructor(id: number,
                createdAt: number,
                description: string,
                senderClass: string,
                level: string) {
        this.id = id;
        this.createdAt = createdAt;
        this.description = description;
        this.senderClass = senderClass;
        this.level = level;
    }
}
