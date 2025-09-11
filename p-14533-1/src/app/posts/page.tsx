'use client'

import { apiFetch } from '@/lib/backend/client'
import { PostDto } from '@/types/post'
import Link from 'next/link'
import { useEffect, useState } from 'react'

export default function Page() {
    const [posts, setPosts] = useState<PostDto[]>([])

    useEffect(() => {
        // (data) => setPosts(data) == setPosts
        apiFetch('/api/v1/posts').then(setPosts)
    }, [])
    if (posts.length === 0) return <div>로딩중...</div>

    return (
        <>
            <h1>글 목록</h1>
            <ul>
                {posts.map((post) => (
                    <li key={post.id}>
                        {post.id} /
                        <Link href={`/posts/${post.id}`}>{post.title}</Link> /
                        {post.content}
                    </li>
                ))}
            </ul>
        </>
    )
}
