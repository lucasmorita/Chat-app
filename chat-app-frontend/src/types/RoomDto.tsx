import { z } from 'zod'

export const OwnerSchema = z.object({
    username: z.string(),
});

export const RoomSchema = z.object({
    id: z.number(),
    name: z.string(),
    // users: z.array(z.string()),
    owner: OwnerSchema,
    description: z.string(),
});

export type RoomDto = z.infer<typeof RoomSchema>;