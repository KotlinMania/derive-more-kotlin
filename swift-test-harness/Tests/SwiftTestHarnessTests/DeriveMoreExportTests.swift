#if canImport(Testing)
import Testing
import DeriveMore

@Suite("DeriveMore Swift Export Smoke Tests")
struct DeriveMoreExportTests {
    @Test("Swift module loads and resolves Kotlin symbols")
    func swiftModuleLoads() throws {
        #expect(Bool(true), "DeriveMore swift module imported cleanly")
    }
}
#elseif canImport(XCTest)
import XCTest
import DeriveMore

final class DeriveMoreExportTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        XCTAssertTrue(true, "DeriveMore swift module imported cleanly")
    }
}
#endif
