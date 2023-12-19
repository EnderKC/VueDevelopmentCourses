import { defineMock } from 'vite-plugin-mock-dev-server'
import Mock from 'mockjs'

const authorMap: Record<string, any> = {
  10001: { id: '10001', name: 'Mark', age: 20 },
  10002: { id: '10001', name: 'John', age: 21 }
}

export default defineMock([
  {
    url: '/api/test',
    body: Mock.mock({
      'list|1-10': [
        {
          'id|+1': 1
        }
      ]
    })
  },
  {
    url: '/api/author/:id',
    body(request) {
      const id = request.params.id as string
      if (id && authorMap[id]) {
        return {
          code: 200,
          message: 'success',
          result: authorMap[id]
        }
      } else {
        return {
          code: 400,
          message: 'author not found'
        }
      }
    }
  }
])
