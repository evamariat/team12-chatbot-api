## Usage
`/spec [feature-name]`

## Context  
- Generate Next.js frontend code for: **$ARGUMENTS**
- Reference Prisma schema, openai.ts, GitHub project
- Follow shadcn/ui + App Router patterns

# Spec.md - Next.js RAG Chatbot Frontend

## Overview
Production-ready **2-page Next.js 15 App Router** chatbot with RAG (Retrieval-Augmented Generation). Upload documents → chat with your knowledge base.

**Status**: Sprint 1 (P0) - GitHub Project item #1 ✅

## Pages Structure

```
app/
├── conversation/           # Chat UI + history (client)
│   └── page.tsx
├── add-docs/              # File upload + indexing (client)
│   └── page.tsx
├── layout.tsx             # Shared root layout
└── globals.css            # Tailwind + custom styles
```

## Core Features

### 1. Conversation Page (`app/conversation/page.tsx`)
**Client-rendered chat interface**
```
✅ Real-time chat with OpenAI (gpt-4o-mini)
✅ Message history (Prisma Message model)  
✅ RAG context injection (pgvector chunks)
✅ Source citations [Doc: filename.pdf #2]
✅ Dehydrated state (server → client JSON)
✅ Streaming responses (AI SDK or raw)
✅ Previous conversations list
```

**Key Components**:
```tsx
- ChatWindow (messages + input)
- MessageBubble (user/assistant + sources)
- ConversationList (server component)
- useChat hook (state + API calls)
```

### 2. AddDocs Page (`app/add-docs/page.tsx`)
**Document upload + indexing pipeline**
```
✅ Drag & drop file upload (PDF, MD, TXT)
✅ Text extraction + chunking (1000 char chunks)
✅ OpenAI embeddings (text-embedding-3-small)  
✅ pgvector storage (Document + DocumentChunk)
✅ Progress indicators
✅ File list + delete
✅ Preview chunks before indexing
```

**Key Components**:
```tsx
- FileUploadZone (drag/drop)
- ChunkPreview (text + embed button)
- DocumentList (server component)
```

## Data Flow

```
1. AddDocs: File → chunks → embeddings → Prisma (DocumentChunk.embedding)
2. Conversation: User msg → embed → pgvector search → RAG prompt → OpenAI → UI
3. Dehydration: Server queries → JSON → client useState<Message[]>
```

## Tech Stack

```
✅ Next.js 15 App Router (Server Components + Client)
✅ TypeScript (Prisma types everywhere)
✅ Tailwind CSS + shadcn/ui (design system)
✅ Prisma + pgvector (Postgres vectors)
✅ Vercel AI SDK (gateway, no API key)
✅ @ai-sdk/vercel (chat.completions + embeddings)
✅ React 19 (useTransition, useOptimistic)
✅ Zod (API validation)
```

## State Management Pattern

**Server Components** → **Dehydration** → **Client useState**
```tsx
// Server Component (app/conversation/page.tsx)
export default async function ConversationPage() {
  const conversations = await prisma.conversation.findMany() // Prisma types
  return (
    <ClientChat 
      initialConversations={JSON.stringify(conversations)} // Dehydrated
      initialMessages={JSON.stringify(messages)} 
    />
  )
}

// Client Component
'use client'
function ClientChat({ initialConversations, initialMessages }: Props) {
  const [messages] = useState<Message[]>(JSON.parse(initialMessages)) // Typed!
  const [conversations] = useState<Conversation[]>(JSON.parse(initialConversations))
}
```

## API Routes

```
✅ POST /api/chat           # RAG conversation (messages → OpenAI + sources)
✅ POST /api/analyze-text   # Text analysis (existing)
✅ POST /api/docs           # Upload + chunk + embed + store
✅ GET  /api/conversations  # List user conversations
✅ GET  /api/docs           # List user documents
```

## UI Components Library (shadcn/ui)
```
- Button, Input, Card, Badge
- Progress, Skeleton, ScrollArea  
- Dialog (file preview), DropdownMenu
- Chat bubbles (markdown + syntax highlight)
- Data table (documents list)
```

## Layout Requirements

**Root `app/layout.tsx`**:
```tsx
- Responsive sidebar (collapsible)
- Top nav (logo + user menu)
- Mobile hamburger menu
- Dark mode toggle
- Tailwind + shadcn theme provider
```

## Success Metrics
```
✅ Loads < 2s (App Router RSC)
✅ 100+ docs indexed (pgvector scale)
✅ Chat latency < 3s (streaming)
✅ Type errors = 0 (Prisma + Zod)
✅ Mobile responsive (iPhone 14+)
✅ Vercel deploy 1-click
```

## Dependencies
```bash
npm i next@15 prisma @prisma/client
@ai-sdk/vercel lucide-react
shadcn-ui@latest tailwindcss
zod react-markdown
```

## GitHub Project Integration
```
Sprint 1: Conversation page (5pt) ✅
Sprint 1: AddDocs page (3pt) ⏳
Sprint 2: RAG backend (8pt)
Sprint 2: pgvector (5pt)
```
