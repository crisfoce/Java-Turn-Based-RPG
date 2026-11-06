.SILENT:
.PHONY: all run clean bin classes

SOURCES := $(shell find . -name "*.java" -not -path "./bin/*")

all: bin classes

classes:
	@javac -d bin $(SOURCES)

run: classes
	@java -cp bin Main

clean:
	@rm -rf bin
	@mkdir -p bin

bin:
	@mkdir -p bin