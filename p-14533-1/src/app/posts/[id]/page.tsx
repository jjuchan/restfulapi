'use client'

import { apiFetch } from '@/lib/backend/client'
import { PostDto } from '@/types/post'
import { useRouter } from 'next/navigation'
import { use, useEffect, useState } from 'react'

export default function Page({ params }: { params: Promise<{ id: number }> }) {
    const [post, setPost] = useState<PostDto | null>(null)

    const { id } = use(params)

    const router = useRouter()

    const deletePost = (id: number) => {
        if (!confirm('정말 삭제하시겠습니까?')) return

        apiFetch(`/api/v1/posts/${id}`, {
            method: 'DELETE',
        }).then((data) => {
            alert(data.msg)
            router.replace('/posts')
        })
    }

    useEffect(() => {
        apiFetch(`/api/v1/posts/${id}`).then(setPost)
    }, [])

    if (post === null) return <div>로딩중...</div>

    return (
        <>
            <h1>게시글 상세페이지</h1>
            <>
                <div>게시글 번호: {post?.id}</div>
                <div>게시글 제목: {post?.title}</div>
                <div>게시글 내용: {post?.content}</div>
            </>

            <div className="flex gap-2">
                <button
                    onClick={() => deletePost(post.id)}
                    className="p-2 rounded border"
                >
                    삭제
                </button>
            </div>
        </>
    )
}
