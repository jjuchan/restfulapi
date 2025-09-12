import { use } from 'react'

export default function Page({ params }: { params: Promise<{ id: number }> }) {
    const { id } = use(params)

    return (
        <>
            <h1>{id}번 글 수정 페이지</h1>

            <form className="flex flex-col gap-2 p-2">
                <input
                    className="border p-2 rounded"
                    type="text"
                    name="title"
                    placeholder="제목"
                    autoFocus
                />
                <textarea
                    className="border p-2 rounded"
                    name="content"
                    placeholder="내용"
                />
                <button className="border p-2 rounded" type="submit">
                    저장
                </button>
            </form>
        </>
    )
}
