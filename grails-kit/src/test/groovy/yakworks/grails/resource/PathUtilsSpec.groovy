package yakworks.grails.resource

import java.nio.file.Path
import java.nio.file.Paths

import spock.lang.Specification
import spock.lang.Unroll

class PathUtilsSpec extends Specification  {

    @Unroll
    void "test removeFileExtension"() {
        expect:
        PathSupport.getBaseName(name) == result

        where:
        name         | result
        'foo'        | 'foo'
        '.foo'       | '.foo'
        'foo.txt'    | 'foo'
        'foo.tar.gz' | 'foo.tar'
        '.foo.bar'   | '.foo'
        'dir/.foo.bar'   | '.foo'
        '/some/dirs/foo.txt'    | 'foo'
    }

    void "test removeFileExtension strip all"() {
        expect:
        PathSupport.removeFileExtension('foo.tar.gz', true) == 'foo'
    }

    void "test match extension"() {
        expect:
        PathSupport.getExtension(name) == result
        // PathUtils.getExtension(Path.of('foo.tar.gz'), true) == 'tar.gz'
        // PathUtils.getExtension('foo') == null
        where:
        name         | result
        'foo'        | null
        '.foo'       | null
        'foo.txt'    | 'txt'
        'foo.tar.gz' | 'gz'
        '.foo.bar'   | 'bar'
        'dir/.foo.bar'   | 'bar'
        '/some/dirs/foo.txt'    | 'txt'

        // PathUtils.getExtension('.foo') == null
    }

    void "test match extension full"() {
        expect:
        PathSupport.getExtension(Paths.get('foo.tar.gz'), true) == 'tar.gz'
    }

    void "test extractMimeType"() {
        expect:
        mimeType == PathSupport.extractMimeType(fileName)

        where:
        fileName     |  mimeType
        'foo.pdf'    |  'application/pdf'
        'foo.png'    |  'image/png'
        'foo.txt'    |  'text/plain'
        'foo.doc'    |  'application/msword'
        'foo.docx'   |  'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
        'foo.xls'    |  'application/vnd.ms-excel'
        'foo.xlsx'    |  'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        'foo.flub'    |  'application/octet-stream'
    }

}
